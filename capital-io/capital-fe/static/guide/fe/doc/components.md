# Stock Capital IO - FE

## 开发篇 (6)

### 组件

#### 组件说明

- 功能中多次出现，可抽取组件复用。
- 组件分为框架级组件、应用级组件。
- 组件可能会涉及到样式封装，需要定义scss样式文件。
- 组件基础需要按照如下代码片段复制。

#### 代码片段

    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style scoped lang="scss">
      @import "style.scss";
    </style>
    
    <!-- Template -->
    <template>
      <div class="component-container xxx-component">
        ...
      </div>
    </template>
    
    <!-- Script -->
    <script>
      import {mapGetters} from 'vuex';
    
      const _Sample = {
        name: 'Sample',
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
      export default _Sample;
    </script>