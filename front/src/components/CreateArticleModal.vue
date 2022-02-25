<template>
  <v-dialog
    v-model="dialog"
    class="article-modal"
    fullscreen
    :scrim="false"
    transition="dialog-bottom-transition">
    <template #activator="{ props }">
      <v-btn
        class="create-modal--button"
        :color="primaryColor"
        icon="mdi-plus"
        size="large"
        v-bind="props" />
    </template>
    <v-card>
      <!-- Custom toolbar -->
      <div class="toolbar">
        <h3 class="text-h6">글쓰기</h3>
        <v-spacer />
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
          <v-list-item
            :title="userNickname">
            <template #prepend>
              <v-avatar class="list-item-avatar">
                <avatar
                  :fullname="userNickname" />
              </v-avatar>
            </template>
          </v-list-item>
          <v-divider />
        </v-list>
        <v-card-text class="none-opacity">
          <v-text-field
            v-model="title"
            label="제목"
            :color="primaryColor"
            :rules="rules"
            hide-details="auto" />
          <v-textarea
            v-model="content"
            solo
            :color="primaryColor"
            name="input-7-1"
            label="내용" />
        </v-card-text>
        <v-row justify="center">
          <v-btn
            :color="primaryColor"
            variant="outlined"
            v-bind="props"
            @click="requestAddArticle">
            저장
          </v-btn>
        </v-row>
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
        userId: 'test@test.com',
        userNickname: 'Jerry Kim',
        title: '',
        content: '',
        rules: [
          value => !!value || 'Required.',
          value => (value && value.length >= 3) || 'Min 3 characters',
        ],
      };
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
      requestAddArticle () {
        // @TODO: 게시글 등록 API 호출
        console.log(this.title);
        console.log(this.content);
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

.create-modal--button {
  position: fixed;
  bottom: 70px;
  right: 30px;
  z-index: 1;
  color: #fff !important;
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
