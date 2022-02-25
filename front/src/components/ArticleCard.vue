<template>
  <v-card
    class="mx-auto align-end">
    <v-card-header>
      <v-card-header-text>
        <v-card-title>
          <h3 class="text-h5 text--primary11 mb-1">
            {{ article.title }}
          </h3>
        </v-card-title>
        <v-card-subtitle>
          {{ article.createdAt }}
          <v-spacer />
          {{ article.createdBy }}
        </v-card-subtitle>
      </v-card-header-text>
    </v-card-header>
    <v-card-text>
      <div class="text--primary ellipsis">
        {{ article.content }}
      </div>
      <div>
        <v-chip
          v-for="(tag, idx) in article.articleHashTags"
          :key="idx"
          class="ma-1"
          size="small"
          :color="primaryColor"
          variant="outlined">
          <v-icon
            left
            icon="mdi-pound" />
          {{ tag }}
        </v-chip>
      </div>
    </v-card-text>

    <v-card-actions>
      <ArticleModal />
        
      <v-spacer />

      <v-icon
        class="mr-2"
        size="small"
        color="surface-variant">
        mdi-eye
      </v-icon>
      {{ article.viewCount }}

      <v-btn
        size="small"
        color="surface-variant"
        variant="text"
        :icon="iconStatus" 
        @click="clickLike" />
      {{ article.likeCount }}

      <v-btn
        size="small"
        color="surface-variant"
        variant="text"
        icon="mdi-share-variant" 
        @click="copyArticleLink" />
    </v-card-actions>
  </v-card>
</template>

<script>
import ArticleModal from './ArticleModal.vue';

export default {
  name: 'ArticleCard',
  components: {
    ArticleModal,
  },
  props: {
    article: {
      type: Object,
      required: true,
    },
  },
  data: () => ({
    primaryColor: '#F43142',
    isLiked: false,
    likeCount: 255,
  }),
  computed: {
    iconStatus() {
      return this.isLiked ? 'mdi-heart' : 'mdi-heart-outline';
    },
  },
  methods: {
    clickLike() {
      this.isLiked = !this.isLiked;
      this.likeCount += this.isLiked ? 1 : -1;
    },
    copyArticleLink() {
      // @TODO: click event link to h3
    },
  },
};
</script>

<style lang="scss" scoped>
.text--primary11 {
  color: #F43142;
}

.ellipsis {
    display: -webkit-box;
    -webkit-line-clamp: 3; /* 라인수 */
    -webkit-box-orient: vertical;
    width: 200px;
    white-space: normal;
    line-height: 1.2em;
    height: 3.6em;
    overflow: hidden;
    text-overflow: ellipsis;
    text-align: left;
    margin-bottom: 8px;
}
</style>
