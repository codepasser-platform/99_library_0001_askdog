<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "style.scss";
</style>

<!-- Template -->
<template>
  <div class="component-container footer-component">
    <div class="container clearfix">
      <div class="footer-summary pull-left p-4">
        <div class="footer-logo"><i class="fa fa-github" aria-hidden="true"></i>Stock Capital - IO
          PLATFORM
        </div>
        <div class="footer-slogan">为团队提供技术文档和辅助开发框架！</div>
      </div>
      <div class="footer-links pull-right d-flex flex-row">
        <div class="d-flex flex-column p-4">
          <div class="footer-links-title">
            <i class="fa fa-info-circle" aria-hidden="true"></i><span>关于</span>
          </div>
          <a class="p-1">关于我们</a>
          <a class="p-1">广告合作</a>
          <a class="p-1">友情链接</a>
          <a class="p-1">招聘</a>
        </div>
        <div class="d-flex flex-column p-4">
          <div class="footer-links-title">
            <i class="fa fa-user-circle" aria-hidden="true"></i><span>新手指南</span>
          </div>
          <a class="p-1">注册登陆</a>
          <a class="p-1">流程管理</a>
        </div>
        <div class="d-flex flex-column p-4">
          <div class="footer-links-title">
            <i class="fa fa-shopping-cart" aria-hidden="true"></i><span>产品</span>
          </div>
          <a class="p-1">易懂</a>
          <a class="p-1">关联交易</a>
          <a class="p-1">上市之家</a>
        </div>
        <div class="d-flex flex-column p-4">
          <div class="footer-links-title">
            <i class="fa fa-handshake-o" aria-hidden="true"></i><span>联系我们</span>
          </div>
          <a class="p-1">新浪微博</a>
          <a class="p-1">电子邮件</a>
        </div>
      </div>
    </div>
  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _Footer = {
    name: 'Footer',
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
  export default _Footer;
</script>
