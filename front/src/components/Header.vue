<template>
  <v-app-bar
    prominent
    color="white"
    prominent-height="100"
    density="compact">
    <v-toolbar-title>
      <v-img
        :src="logo"
        width="200"
        alt="logo" />
    </v-toolbar-title>

    <v-spacer />

    <v-btn icon>
      <v-icon
        :size="iconSize">
        mdi-magnify
      </v-icon>
    </v-btn>

    <v-btn icon>
      <v-icon
        :size="iconSize">
        mdi-account
      </v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script>
import logo from '../assets/logo_variation_black.svg';

export default {
  name: 'Header',

  data: () => ({
    logo,
    iconSize: '30',
  }),
};
</script>
