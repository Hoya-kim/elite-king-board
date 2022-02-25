<template>
  <v-dialog
    v-model="dialog"
    class="article-modal"
    fullscreen
    :scrim="false"
    transition="dialog-bottom-transition">
    <template #activator="{ props }">
      <v-btn
        :color="primaryColor"
        variant="outlined"
        v-bind="props">
        더보기
      </v-btn>
    </template>
    <v-card>
      <!-- Custom toolbar -->
      <div class="toolbar">
        <h3 class="text-h6">{{ article.title }}</h3>
        <v-spacer />
        <v-btn
          icon
          dark
          :color="primaryColor"
          @click="dialog = false">
          <v-icon>mdi-heart</v-icon>
        </v-btn>
        <v-btn
          :color="primaryColor"
          icon
          @click="dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </div>
      <!-- Details -->
      <div class="scrollable-container">
        <v-list>
          <!-- prepend-avatar="https://cdn.vuetifyjs.com/images/john.png" -->
          <v-list-item
            :title="article.modifiedBy"
            :subtitle="article.createdAt">
            <template #prepend>
              <v-avatar class="list-item-avatar">
                <avatar
                  fullname="Jerry Kim" />
              </v-avatar>
            </template>
            <template #append>
              <!-- @TODO: 작성자와 일치하면 -->
              날짜 및 삭제 버튼
            </template>
          </v-list-item>
          <v-divider />
        </v-list>
        <v-card-text class="none-opacity">
          <div>
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
        <v-divider />
        <!-- Comments -->
        <v-card-text class="none-opacity">
          <h4 class="text-body-1">댓글</h4>
          <v-list three-line>
            <div
              v-for="(comment, idx) in article.comments"
              :key="idx">
              <v-list-item
                :prepend-avatar="comment.prependAvatar"
                :title="comment.author"
                :subtitle="comment.content" />
              <v-divider />
            </div>
          </v-list>
        </v-card-text>
      </div>
    </v-card>
  </v-dialog>
</template>
<script>
import Avatar from 'vue-avatar-component';

  export default {
    components: {
      Avatar,
    },
    data () {
      return {
        primaryColor: '#F43142',
        dialog: false,
        
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
          comments: [
            {
              prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
              author: '작성자 아이디',
              content: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptas dolore facilis veritatis id, nam totam officia minima consequatur sapiente vel repellendus ea sint officiis numquam similique maxime. Quas, vero eveniet!',
            },
            {
              prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
              author: '작성자 아이디',
              content: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptas dolore facilis veritatis id, nam totam officia minima consequatur sapiente vel repellendus ea sint officiis numquam similique maxime. Quas, vero eveniet!',
            },
            {
              prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
              author: '작성자 아이디',
              content: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptas dolore facilis veritatis id, nam totam officia minima consequatur sapiente vel repellendus ea sint officiis numquam similique maxime. Quas, vero eveniet!',
            },
            {
              prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
              author: '작성자 아이디',
              content: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptas dolore facilis veritatis id, nam totam officia minima consequatur sapiente vel repellendus ea sint officiis numquam similique maxime. Quas, vero eveniet!',
            },
            {
              prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/5.jpg',
              author: '작성자 아이디',
              content: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptas dolore facilis veritatis id, nam totam officia minima consequatur sapiente vel repellendus ea sint officiis numquam similique maxime. Quas, vero eveniet!',
            },
          ],
        },
      };
    },
    computed: { // Turn our data into viewable values with these functions
      identicon: function() {
        // eslint-disable-next-line no-undef
        return jdenticon.toSvg('user127', 200);
      },
    },
    mounted() {
      document.addEventListener('backbutton', this.overideAndroidBackButton);
    },
    methods: {
      overideAndroidBackButton() {
        this.dialog ? this.turnOffModal() : navigator.app.exitApp();
      },
      turnOffModal() {
        this.dialog = false;
      },
    },
  };
</script>
<style>
.dialog-bottom-transition-enter-active,
.dialog-bottom-transition-leave-active {
  transition: transform .2s ease-in-out;
}

.article-modal {
  margin-top: 84px; 
  padding-bottom: -84px;
}

.scrollable-container {
  overflow: auto;
  height: calc(100vh - 84px - 64px);
}

.toolbar {
  background-color: #F43142;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px;
  padding-left: 16px;
}

.none-opacity {
  opacity: 1;
}

.list-item-avatar {
    -webkit-margin-end: 16px;
    margin-inline-end: 16px;
}
</style>
