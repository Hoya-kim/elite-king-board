<template>
  <v-app>
    <Header />
    <v-main>
      <HelloWorld />
    </v-main>
  </v-app>
</template>

<script>
  import Header from './components/Header.vue';
  import HelloWorld from './components/HelloWorld.vue';

  export default {
    name: 'App',

    components: {
      Header,
      HelloWorld,
    },

    data: () => ({
      //
    }),
  };
</script>
