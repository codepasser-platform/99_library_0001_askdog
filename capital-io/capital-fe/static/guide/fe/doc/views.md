# Stock Capital IO - FE

## 开发篇 (5)

### 视图

#### 组件说明

- 视图中不要单独定义样式，否则框架样式可能被覆盖。
- 视图基础需要按照如下代码片段复制。

#### 代码片段

    <style scoped lang="scss">
      ...
    </style>
    <!-- Template -->
    <template>
      <div class="view-container container-fluid">
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