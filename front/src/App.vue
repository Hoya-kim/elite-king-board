<template>
  <v-app>
    <Header />
    <v-main>
      <CardsContainer />
    </v-main>
    <SignInModal />
  </v-app>
</template>

<script>
  import Header from './components/Header.vue';
  import CardsContainer from './components/CardsContainer.vue';
  import SignInModal from './components/SignInModal.vue';

  export default {
    name: 'App',

    components: {
      Header,
      CardsContainer,
      SignInModal,
    },

    data: () => ({
      dialog: false,
    }),
  };
</script>
