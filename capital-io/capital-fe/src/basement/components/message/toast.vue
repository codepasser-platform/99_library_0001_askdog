<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "style.scss";
</style>

<!-- Template -->
<template>
  <div class="component-container message-component">

    <b-alert dismissible
             :variant="variant"
             :show="dismissCountDown"
             @dismissed="dismissCountDown=0"
             @dismiss-count-down="countDownChanged"
             class="container fixed-top w-25 mt-5">
      {{content}}
    </b-alert>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _Toast = {
    name: 'Toast',
    created: function () {
    },
    updated: function () {
    },
    mounted: function () {
    },
    activated: function () {
    },
    data: function () {
      return {
        dismissSecs: 3,
        dismissCountDown: 0,
        variant: 'info', // primary secondary success danger warning info light dark
        content: '',
      };
    },
    methods: {
      countDownChanged(dismissCountDown) {
        this.dismissCountDown = dismissCountDown
      },
      show(_content, _variant) {
        console.log('Toast -> show', _content, _variant);
        this.content = _content;
        this.variant = _variant || 'info';
        this.dismissCountDown = this.dismissSecs
      }
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _Toast;
</script>
