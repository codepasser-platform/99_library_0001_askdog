<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
  @import "style.scss";
</style>

<!-- Template -->
<template>
  <div class="component-container message-component">

    <b-modal centered no-close-on-backdrop no-close-on-esc
             size="sm"
             button-size="sm"
             title-tag="h6"
             footer-class="border-0 justify-content-center"
             ok-title="确定"
             ok-variant="outline-primary"
             cancel-title="取消"
             cancel-variant="outline-secondary"
             :title="title"
             :ok-only="confirmMode"
             @ok="confirmHandler()"
             @cancel="cancelHandler()"
             ref="confirmModal">
      <p>{{content}}</p>
    </b-modal>

  </div>
</template>

<!-- Script -->
<script>
  import {mapGetters} from 'vuex';

  const _Confirm = {
    name: 'Confirm',
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
        confirmMode: false,
        title: '提醒',
        content: '',
        onConfirmCallback: undefined,
        onCancelCallback: undefined
      };
    },
    methods: {
      show(_content, _confirm, _cancel, _title) {
        console.log('Confirm > open', _title);
        this.title = _title || '提醒';
        this.content = _content || '';
        this.onConfirmCallback = _confirm;
        if (_cancel) {
          this.confirmMode = false;
        } else {
          this.confirmMode = true;
        }
        this.onCancelCallback = _cancel;
        this.$refs.confirmModal.show();
      },
      confirmHandler() {
        console.log('Confirm > confirmHandler');
        if (this.onConfirmCallback) {
          this.onConfirmCallback();
        }
      },
      cancelHandler() {
        console.log('Confirm > cancelHandler');
        if (this.onCancelCallback) {
          this.onCancelCallback();
        }
      }
    },
    components: {},
    computed: {
      ...mapGetters([])
    }
  };
  export default _Confirm;
</script>
