<template>
  <v-app>
    <Header />
    <v-main>
      <CardsContainer />
    </v-main>
    <SignIn />
  </v-app>
</template>

<script>
  import Header from './components/Header.vue';
  import CardsContainer from './components/CardsContainer.vue';
  import SignIn from './components/SignIn.vue';

  export default {
    name: 'App',

    components: {
      Header,
      CardsContainer,
      SignIn,
    },

    data: () => ({
      dialog: false,
    }),
  };
</script>
