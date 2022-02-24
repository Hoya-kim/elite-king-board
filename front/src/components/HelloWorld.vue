<template>
  <v-container
    class="mx-auto max-width-900">
    <v-row dense>
      <v-col
        :cols="colSize">
        <ArticleCard />
      </v-col>
      <v-col
        :cols="colSize">
        <ArticleCard />
      </v-col>
      <v-col
        :cols="colSize">
        <ArticleCard />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import logo from '../assets/logo.svg';
import ArticleCard from './ArticleCard.vue';

export default {
  name: 'HelloWorld',

  components: {
    ArticleCard,
  },

  data: () => ({
    logo,
    colSize: '12',
  }),
  mounted() {
    this.getColSize();
    window.addEventListener('resize', this.getColSize);
	},
  beforeUnmount() {
    window.removeEventListener('resize', this.getColSize);
  },
  methods: {
    getColSize() {
      this.colSize = window.innerWidth > 600 ? '6' : '12';
    },
  },
};
</script>


<style lang="scss" scoped>
.max-width-900 {
  max-width: 900px;
}
</style>
