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
          {{ article.modifiedBy }}
        </v-card-subtitle>
      </v-card-header-text>
    </v-card-header>
    <v-card-text>
      <div class="text--primary">
        {{ article.content }}
      </div>
      <div>
        <v-chip
          v-for="(tag, idx) in article.hashTags"
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
  data: () => ({
    primaryColor: '#F43142',
    article: {
      // sample data
      id: '0',
      title: '게시글 제목',
      content: '게시글 본문, Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptas dolore facilis veritatis id, nam totam officia minima consequatur sapiente vel repellendus ea sint officiis numquam similique maxime. Quas, vero eveniet!',
      viewCount: 3000,
      likeCount: 256,
      createdAt: '2020-01-01',
      modifiedBy: '게시자',
      hashTags: ['#해쉬태그1', '#해쉬태그2'],
      isLiked: true,
    },
  }),
  computed: {
    iconStatus() {
      return this.article.isLiked ? 'mdi-heart' : 'mdi-heart-outline';
    },
  },
  methods: {
    clickLike() {
      this.article.isLiked = !this.article.isLiked;
      this.article.likeCount += this.article.isLiked ? 1 : -1;
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
</style>
