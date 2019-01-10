<style scoped lang="scss">
  .home-container {
    background: $white;
  }

  /**********************************************************************/
  .first-col {
    padding-right: 24px;
    border-right: 1px solid #bababa;
    height: auto;
  }

  .title {
    font-size: 22px;
    border-bottom: 1px solid #bababa;
  }
  p {
    font-size: 14px;
    color: #888888;
    font-weight: lighter;
  }

  .first-col ul {
    margin-top: 10px;
    margin-bottom: 25px;
  }

  .h206 {
    height: 206px;
    overflow: hidden;
  }

  .h206 li {
    font-size: 18px;
    line-height: 34px;
    padding-left: 10px;
    color: #434343;
  }
  .mt-20{
    margin-top: 20px
  }
  .pannel-body{
    padding: 0 20px;
    overflow: hidden;
  }
  .pannel-body .fast-item{
    position: relative;
    padding: 26px 0;
    border-bottom: 1px solid #ebebeb;
    cursor: pointer;
    overflow: hidden;
  }
 .pannel-body .fast-item .item-logo{
    margin-right: 10px;
    width: 36px;
    height: 36px;
    border: 1px solid #e4e4e4;
  }
  .pannel-body .fast-item .item-logo .img-box{
    width: 36px;
    height: 36px;
    display: table-cell;
    text-align: center;
    vertical-align: middle;
  }
  .pannel-body .fast-item .item-logo .img-box img {
    max-width: 36px;
    vertical-align: middle;
  }
  .pannel-body .fast-item .item-title{
    height: 40px;
    color: #333;
    line-height: 20px;
    font-size: 14px;
    overflow: hidden;
  }
  .pannel-body .fast-item .item-time{
    position: absolute;
    right: 0;
    bottom: 6px;
    color: #aaa;
    font-size: 12px;
  }
</style>
<!-- Template -->
<template>
  <div class="view-container container-fluid home-container">
    <!-- 轮播 -->
    <b-carousel id="carousel1"
                style="text-shadow: 1px 1px 2px #333;"
                controls
                indicators
                background="#ababab"
                :interval="4000"
                img-width="1024"
                img-height="480"
                v-model="slide"
                @sliding-start="onSlideStart"
                @sliding-end="onSlideEnd">
      <b-carousel-slide caption="First slide"
                        text="Nulla vitae elit libero, a pharetra augue mollis interdum."
                        img-src="https://picsum.photos/1024/480/?image=52"
      ></b-carousel-slide>
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=54">
        <h1>Hello world!</h1>
      </b-carousel-slide>

      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=58">
      </b-carousel-slide>
      <b-carousel-slide>
        <img slot="img" class="d-block img-fluid w-100" width="1024" height="480"
             src="https://picsum.photos/1024/480/?image=55" alt="image slot">
      </b-carousel-slide>

      <b-carousel-slide caption="Blank Image" img-blank img-alt="Blank image">
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
          eros felis, tincidunt a tincidunt eget, convallis vel est. Ut pellentesque
          ut lacus vel interdum.
        </p>
      </b-carousel-slide>
    </b-carousel>

    <b-row>
      <b-col class="first-col">
        <!--上市工作动态-->
        <div class="title">
          <a href="#" target="_blank">上市工作动态</a>
        </div>
        <ul class="h206">
          <li>
            <a href="#">新闻</a>&nbsp;|
            <a href="#">我省企业锐科激光成功在深圳 证券交易所上市募集资金12.2亿元</a>
          </li>
        </ul>
        <!--沪、深交易所服务湖北基地-->
        <div class="title">沪、深交易所服务湖北基地</div>
        <p><a href="#">董秘培训</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">走进交易所</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
          href="#">领军人才培训</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">生物医药</a></p>
        <!--专题知识-->
        <div class="title">专题知识</div>
        <ul>
          <li><a href="#">上市自评</a>&nbsp;|<a>业务</a><a>财务</a><a>法务</a></li>
          <li><a href="#">上市进程</a>&nbsp;|<a>筹备</a><a>股改</a><a>尽调</a><a>报辅</a><a>报会</a></li>
          <li><a href="#">上市地选择</a>&nbsp;|<a>主板</a><a>中小板</a><a>创业板</a><a>创业板</a>境外</li>
          <li><a href="#">上市成本</a>&nbsp;|<a>券商</a><a>券商</a><a>律所</a></li>
        </ul>
        <!--互动培训-->
        <div class="title">
          <a href="#" target="_blank">互动培训</a>
        </div>
        <div class="fast-item">
          <div><span>2018-7-23</span><span>中国-武汉</span></div>
          <div class="item-title">
            <a href="#">“企业上市领军人才”系列培训(财务模块)</a>
          </div>
        </div>
      </b-col>
      <b-col>
        <!--支持政策-->
        <div class="title">支持政策</div>
        <ul>
          <li><a href="#">省政府</a>&nbsp;|<a>关于进一步支持企业上市工作意见</a></li>
          <li><a href="#">省发改委</a>&nbsp;|<a>xxxx意见</a></li>
          <li><a href="#">省经信委</a>&nbsp;|<a>xxxx意见</a></li>
          <li><a href="#">省科技厅</a>&nbsp;|<a>xxxx意见</a></li>
        </ul>

        <!--投资机构-->
        <div class="title">
          <a href="#" target="_blank">投资机构</a>
        </div>
        <p><a href="#">先进制造</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">互联网</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">生物医药</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
          href="#">其它</a></p>
        <ul class="h206">
          <li><a href="#">红杉资本</a></li>
          <li><a href="#">光谷智慧基金</a></li>
        </ul>
        <!--金融机构-->
        <div class="title">
          <a href="#" target="_blank">金融机构</a>
        </div>
        <ul class="h206">
          <li><a href="#">中国银行科技金融贷</a></li>
          <li><a href="#">招商银行股权质押贷</a></li>
        </ul>
        <!--中介机构-->
        <div class="title">
          <a href="#" target="_blank">中介机构</a>
        </div>
        <p><a href="#">券商</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">会所</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">律所</a></p>
        <ul class="h206">
          <li><a href="#">中国银行科技金融贷</a></li>
          <li><a href="#">招商银行股权质押贷</a></li>
        </ul>
      </b-col>
      <b-col>
        <!--绿色通道-->
        <div class="title">
          <a href="#" target="_blank">绿色通道</a>
        </div>
        <b-input-group class="mt-20">
          <b-form-input  type = “number”  min = “0.00” ></b-form-input>
          <b-input-group-append >
          <b-btn  variant = “outline-secondary” >按钮</b-btn>
          </b-input-group-append >
        </b-input-group >
        <!--融资速递-->
        <div class="title">
          <a href="#" target="_blank">融资速递</a>
        </div>
        <div class="pannel-body">
          <div class="fast-item">
            <div class="item-logo float-left">
              <div class="img-box">
                <img src="http://img4.cyzone.cn/uploadfile/2018/0731/thumb_100_100_2018073101475070.png">
              </div>
            </div>
            <div class="item-title">
              <a href="#">良品铺子拟pre-ipo轮融资2亿元 8月底完成...</a>
            </div>
            <div class="item-time">2018-7-23</div>
          </div>
          <div class="fast-item">
            <div class="item-logo float-left">
              <div class="img-box">
                <img src="http://img4.cyzone.cn/uploadfile/2018/0731/thumb_100_100_2018073101475070.png">
              </div>
            </div>
            <div class="item-title">
              <a href="#">良品铺子拟pre-ipo轮融资2亿元 8月底完成...</a>
            </div>
            <div class="item-time">2018-7-23</div>
          </div>
          <div class="fast-item">
            <div class="item-logo float-left">
              <div class="img-box">
                <img src="http://img4.cyzone.cn/uploadfile/2018/0731/thumb_100_100_2018073101475070.png">
              </div>
            </div>
            <div class="item-title">
              <a href="#">良品铺子拟pre-ipo轮融资2亿元 8月底完成...</a>
            </div>
            <div class="item-time">2018-7-23</div>
          </div>
          <div class="fast-item">
            <div class="item-logo float-left">
              <div class="img-box">
                <img src="http://img4.cyzone.cn/uploadfile/2018/0731/thumb_100_100_2018073101475070.png">
              </div>
            </div>
            <div class="item-title">
              <a href="#">良品铺子拟pre-ipo轮融资2亿元 8月底完成...</a>
            </div>
            <div class="item-time">2018-7-23</div>
          </div>
          <div class="fast-item">
            <div class="item-logo float-left">
              <div class="img-box">
                <img src="http://img4.cyzone.cn/uploadfile/2018/0731/thumb_100_100_2018073101475070.png">
              </div>
            </div>
            <div class="item-title">
              <a href="#">良品铺子拟pre-ipo轮融资2亿元 8月底完成...</a>
            </div>
            <div class="item-time">2018-7-23</div>
          </div>
        </div>
        <div class="title">
          <a href="#" target="_blank">上下游合作</a>
        </div>
      </b-col>
    </b-row>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _Home = {
    name: 'Home',
    created: function () {
    },
    updated: function () {
    },
    mounted: function () {
    },
    activated: function () {
    },
    data: function () {
      return {};
    },
    methods: {},
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _Home;
</script>
