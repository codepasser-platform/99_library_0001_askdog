package com.askdog.service.impl.product;

import com.askdog.dao.repository.CouponRepository;
import com.askdog.dao.repository.ProductRepository;
import com.askdog.dao.repository.StoreRepository;
import com.askdog.dao.repository.UserRepository;
import com.askdog.dao.repository.mongo.ProductStatisticsRepository;
import com.askdog.dao.repository.mongo.VoteRepository;
import com.askdog.model.data.Actions.VoteAction;
import com.askdog.model.data.ProductStatistics;
import com.askdog.model.data.StorageRecord;
import com.askdog.model.data.inner.EntityType;
import com.askdog.model.data.inner.ResourceDescription;
import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.entity.Product;
import com.askdog.service.CouponService;
import com.askdog.service.ProductService;
import com.askdog.service.StoreService;
import com.askdog.service.UserService;
import com.askdog.service.bo.common.PagedData;
import com.askdog.service.bo.product.ProductCreation;
import com.askdog.service.bo.product.ProductDetail;
import com.askdog.service.bo.product.ProductDetailBasic;
import com.askdog.service.bo.product.ProductUpdate;
import com.askdog.service.bo.product.productdetail.ProductPageDetail;
import com.askdog.service.bo.storedetail.StorePageDetail_Product;
import com.askdog.service.exception.ConflictException;
import com.askdog.service.exception.ForbiddenException;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.impl.cell.ProductCell;
import com.askdog.service.impl.event.TriggerEvent;
import com.askdog.service.impl.event.TriggerEvent.TriggerEventItem;
import com.askdog.service.impl.storage.StorageRecorder;
import com.askdog.service.impl.storage.aliyun.description.OssVideoResourceDescription;
import com.askdog.web.common.async.AsyncCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.askdog.common.utils.Conditions.checkState;
import static com.askdog.model.common.EventType.*;
import static com.askdog.model.common.State.DELETED;
import static com.askdog.model.data.inner.VoteDirection.NONE;
import static com.askdog.service.bo.common.PagedDataUtils.rePage;
import static com.askdog.service.exception.ConflictException.Error.VOTE;
import static com.askdog.service.exception.ForbiddenException.Error.DELETE_PRODUCT;
import static com.askdog.service.exception.ForbiddenException.Error.UPDATE_PRODUCT;
import static com.askdog.service.exception.NotFoundException.Error.PRODUCT;
import static com.askdog.service.exception.NotFoundException.Error.USER;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.toList;

@RestController
public class ProductServiceImpl implements ProductService {

    @Autowired private UserService userService;
    @Autowired private CouponService couponService;
    @Autowired private ProductCell productCell;
    @Autowired private StoreService storeService;
    @Autowired private StorageRecorder storageRecorder;

    @Autowired private UserRepository userRepository;
    @Autowired private StoreRepository storeRepository;
    @Autowired private CouponRepository couponRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private VoteRepository voteRepository;
    @Autowired private ProductStatisticsRepository productStatisticsRepository;
    @Autowired private AsyncCaller asyncCaller;

    @Override
    public ProductDetail getDetail(@RequestParam(value = "userId", required = false) Long userId,
                                   @PathVariable(value = "productId") Long productId,
                                   @RequestParam(value = "allow_deleted") boolean allowDeleted) {
        ProductDetailBasic productDetailBasic = productCell.getBasic(productId);
        checkState(allowDeleted || !productDetailBasic.isDeleted(), () -> new NotFoundException(PRODUCT));
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(productDetailBasic.getId());
        productDetail.setName(productDetailBasic.getName());
        productDetail.setDescription(productDetailBasic.getDescription());

        if (productDetailBasic.getVideoId() != null) {
            ResourceDescription description = storageRecorder.getResource(productDetailBasic.getVideoId()).getDescription();
            if (description instanceof OssVideoResourceDescription) {
                productDetail.setVideo(((OssVideoResourceDescription) description).getVideo());
            }
        }

        if (productDetailBasic.getCoverId() != null) {
            ResourceDescription description = storageRecorder.getResource(productDetailBasic.getCoverId()).getDescription();
            productDetail.setCoverUrl(description.getResourceUrl());
        }

        if (productDetailBasic.getPictures() != null) {
            productDetail.setPictures(productDetailBasic.getPictures().stream()
                    .map(storageRecorder::getResource)
                    .map(StorageRecord::getDescription)
                    .map(ResourceDescription::getResourceUrl).collect(Collectors.toList()));
        }

        productDetail.setStore(storeService.findDetail(productDetailBasic.getStoreId(), false));
        productDetail.setCoupons(productDetailBasic.getCoupons().stream()
                .map(couponId -> couponService.getDetail(couponId)).collect(toList()));
        productDetail.setState(productDetailBasic.getState());
        productDetail.setTags(productDetailBasic.getTags());
        productDetail.setCreationTime(productDetailBasic.getCreationTime());
        productDetail.setCreationUser(userService.findDetail(productDetailBasic.getCreationUserId()));
        productDetail.setDeleted(productDetailBasic.isDeleted());

        if (userId != null) {
            productCell.fillInState(userId, productDetail);
        }

        productDetail.setStatistics(productCell.getStatistic(productId));

        return productDetail;
    }

    @Override
    public ProductPageDetail getPageDetail(@RequestParam(value = "userId", required = false) Long userId,
                                           @PathVariable(value = "productId") Long productId) {
        ProductPageDetail productPageDetail = new ProductPageDetail().from(getDetail(userId, productId, false));

        asyncCaller.asyncCall(() -> {
            productStatisticsRepository.increaseViewCount(productId);

            ProductStatistics statistics = productStatisticsRepository.findByProductId(productId).orElseGet(() -> {
                ProductStatistics productStatistics = new ProductStatistics();
                productStatistics.setProductId(productId);
                return productStatistics;
            });

            if (Math.random() > 0.9) {
                statistics.setUpVoteCount(statistics.getUpVoteCount() + 1);
                productStatisticsRepository.save(statistics);
            }

            productCell.refreshStatisticCache(productId);

        });

        return productPageDetail;
    }

    @Override
    public PagedData<StorePageDetail_Product> getStoreProduct(@RequestParam(value = "userId", required = false) Long userId,
                                                              @RequestParam(value = "store_id") Long storeId,
                                                              @RequestBody Pageable pageable) {
        Page<Product> products = productRepository.findByStore_IdAndStateNotIn(storeId, newHashSet(DELETED), pageable);
        return rePage(products, pageable, product -> new StorePageDetail_Product().from(getDetail(userId, product.getId(), false)));
    }

    @Override
    public PagedData<ProductDetail> getManageStoreProduct(@RequestParam(value = "userId", required = false) Long userId,
                                                          @RequestParam(value = "store_id") Long storeId,
                                                          @RequestBody Pageable pageable) {
        Page<Product> products = productRepository.findByStore_Id(storeId, pageable);
        return rePage(products, pageable, product -> getDetail(userId, product.getId(), true));
    }

    @Nonnull
    @Override
    public ProductPageDetail create(@RequestParam(value = "user_id") Long userId,
                                    @RequestBody @Valid ProductCreation productCreation) {
        if (productCreation.getVideoId() != null) {
            storageRecorder.assertVideoValid(productCreation.getVideoId());
        }

        if (productCreation.getCoverId() != null) {
            storageRecorder.assertValid(productCreation.getCoverId());
        }

        if (productCreation.getPictures() != null) {
            productCreation.getPictures().forEach(storageRecorder::assertValid);
        }

        Product product = new Product();
        product.setName(productCreation.getName());
        product.setDescription(productCreation.getDescription());
        product.setVideoId(productCreation.getVideoId());
        product.setCoverId(productCreation.getCoverId());
        product.setPictures(productCreation.getPictures());
        product.setStore(storeRepository.findOne(productCreation.getStoreId()));
        product.setTags(productCreation.getTags());

        Stream<Long> stream = productCreation.getCoupons().stream();
        product.setCoupons(stream.map(resourceId -> couponRepository.findOne(resourceId)).collect(toList()));

        product.setCreationUser(userRepository.findOne(userId));
        product.setCreationTime(new Date());
        Product savedProduct = productRepository.save(product);

        ProductStatistics productStatistics = new ProductStatistics();
        productStatistics.setProductId(savedProduct.getId());
        productStatistics.setUpVoteCount(50 + (int) (1 + Math.random() * 30));
        productStatisticsRepository.save(productStatistics);

        productCell.refreshBasicCache(savedProduct.getId());
        return new ProductPageDetail().from(getDetail(userId, savedProduct.getId(), false));
    }

    @Nonnull
    @Override
    public ProductPageDetail update(@RequestParam(value = "user_id") Long userId,
                                    @RequestBody @Valid ProductUpdate productUpdate) {
        ProductDetail productDetail = getDetail(userId, productUpdate.getId(), false);
        checkState(productDetail.isEditable(), () -> new ForbiddenException(UPDATE_PRODUCT));

        if (productUpdate.getVideoId() != null) {
            storageRecorder.assertVideoValid(productUpdate.getVideoId());
        }

        if (productUpdate.getCoverId() != null) {
            storageRecorder.assertValid(productUpdate.getCoverId());
        }

        if (productUpdate.getPictures() != null) {
            productUpdate.getPictures().forEach(storageRecorder::assertValid);
        }

        Product product = productCell.findExist(productUpdate.getId());
        product.setName(productUpdate.getName());
        product.setDescription(productUpdate.getDescription());
        product.setVideoId(productUpdate.getVideoId());
        product.setCoverId(productUpdate.getCoverId());
        product.setPictures(productUpdate.getPictures());
        product.setTags(productUpdate.getTags());

        Stream<Long> stream = productUpdate.getCoupons().stream();
        product.setCoupons(stream.map(resourceId -> couponRepository.findOne(resourceId)).collect(toList()));

        Product savedProduct = productRepository.save(product);

        productCell.refreshBasicCache(savedProduct.getId());
        return new ProductPageDetail().from(getDetail(userId, savedProduct.getId(), false));
    }

    @Override
    public void delete(@RequestParam(value = "user_id") Long userId,
                       @RequestParam(value = "product_id") Long productId) {
        ProductDetail productDetail = getDetail(userId, productId, false);
        checkState(productDetail.isDeletable(), () -> new ForbiddenException(DELETE_PRODUCT));

        Product product = productCell.findExist(productId);
        product.setState(DELETED);
        productRepository.save(product);
        productCell.refreshBasicCache(product.getId());
    }

    @Override
    @Nonnull
    @TriggerEvent({
            @TriggerEventItem(condition = "direction == T(com.askdog.model.data.inner.VoteDirection).UP", performer = "userId", eventType = UP_VOTE_PRODUCT, target = "productId"),
            @TriggerEventItem(condition = "direction == T(com.askdog.model.data.inner.VoteDirection).DOWN", performer = "userId", eventType = DOWN_VOTE_PRODUCT, target = "productId"),
            @TriggerEventItem(condition = "direction == T(com.askdog.model.data.inner.VoteDirection).NONE", performer = "userId", eventType = UN_VOTE_PRODUCT, target = "productId")
    })
    public void vote(@RequestParam("userId") Long userId,
                     @PathVariable("productId") Long productId,
                     @RequestParam(value = "direction") VoteDirection direction) {
        checkState(userService.isExists(userId), () -> new NotFoundException(USER));
        ProductDetail productDetail = getDetail(userId, productId, false);
        VoteDirection currentVoteDirection = productDetail.getVote();
        checkState(direction != currentVoteDirection, () -> new ConflictException(VOTE));

        voteRepository.deleteByUserAndTargetId(userId, productId);

        if (direction != NONE) {
            VoteAction voteAction = new VoteAction();
            voteAction.setUser(userId);
            voteAction.setDirection(direction);
            voteAction.setTargetId(productId);
            voteAction.setTargetType(EntityType.PRODUCT);
            voteRepository.save(voteAction);
        }

        asyncCaller.asyncCall(() -> {
            productStatisticsRepository.updateVoteCount(productId, currentVoteDirection, direction);
            productCell.refreshStatisticCache(productId);
        });
    }

}
