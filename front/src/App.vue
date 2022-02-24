<template>
  <v-app>
    <Header />
    <v-main>
      <CardsContainer />
    </v-main>
  </v-app>
</template>

<script>
  import Header from './components/Header.vue';
  import CardsContainer from './components/CardsContainer.vue';

  export default {
    name: 'App',

    components: {
      Header,
      CardsContainer,
    },

    data: () => ({
      //
    }),
  };
</script>
