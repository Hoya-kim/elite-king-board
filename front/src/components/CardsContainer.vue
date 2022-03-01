<template>
  <v-container
    class="mx-auto max-width-900">
    <v-row dense>
      <v-col
        v-for="article in articles"
        :key="article.id"
        :cols="colSize">
        <ArticleCard :article="article" />
      </v-col>
      <!-- <v-col
        :cols="colSize">
        <ArticleCard :article="article" />
      </v-col>
      <v-col
        :cols="colSize">
        <ArticleCard :article="article" />
      </v-col> -->
    </v-row>
    <div class="text-center">
      <v-pagination
        v-model="page"
        :length="15"
        :total-visible="7" 
        :color="primaryColor"
        rounded="circle" />
    </div>
  </v-container>
</template>

<script>
import ArticleCard from './ArticleCard.vue';
import { FETCH_ARTICLES } from '../store/articles/types.js';

export default {
  name: 'CardsContainer',

  components: {
    ArticleCard,
  },

  data: () => ({
    colSize: '12',
    primaryColor: '#F43142',
    articles: [],
  }),
  created() {
    this.fetchArticles();
  },
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
    async fetchArticles() {
      await this.$store.dispatch(FETCH_ARTICLES);
      const articles = this.$store.getters[FETCH_ARTICLES];
      console.log(articles);
      this.articles = articles;
    },
  },
};
</script>


<style lang="scss" scoped>
.max-width-900 {
  max-width: 900px;
}
</style>
