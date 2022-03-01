<template>
  <v-app>
    <Header />
    <v-main>
      <CardsContainer />
      <CreateArticleModal />
    </v-main>
    <SignInModal />
  </v-app>
</template>

<script>
  import Header from './components/Header.vue';
  import CardsContainer from './components/CardsContainer.vue';
  import SignInModal from './components/SignInModal.vue';
  import CreateArticleModal from './components/CreateArticleModal.vue';
  import { FETCH_USER } from './store/user/types.js';

  export default {
    name: 'App',

    components: {
      Header,
      CardsContainer,
      SignInModal,
      CreateArticleModal,
    },

    data: () => ({
      dialog: false,
    }),
    mounted() {
      window.addEventListener('message', this.receiveMessage, false);
      window.receiveToken = this.receiveToken;
    },
    beforeUnmount() {
      window.removeEventListner('message', this.receiveMessage);
    },
    methods: {
      receiveMessage(event) {
        // @todo
        console.log('origin: ', event.origin); // 메시지를 보낸 곳
        console.log('message: ', event.data); // 보낸 메시지
        this.$store.dispatch(FETCH_USER, event);
        console.log('???', this.$store.getters.FETCH_USER); 
      },
      receiveToken() {
        console.log('안드로이드 호출 테스트: receiveToken');
        // console.log(data);
      },
    },
  };
</script>
