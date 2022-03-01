import { createApp } from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import { loadFonts } from './plugins/webfontloader';
import store from './store';

loadFonts();

createApp(App)
  .use(vuetify)
  .use(store)
  .mount('#app');

  // Import the functions you need from the SDKs you need
import { initializeApp } from 'firebase/app';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: 'AIzaSyBzrtn2Z5lQ-9O-5aDwmfJ2MkfScvms_a0',
  authDomain: 'elite-king-board.firebaseapp.com',
  projectId: 'elite-king-board',
  storageBucket: 'elite-king-board.appspot.com',
  messagingSenderId: '1092461741679',
  appId: '1:1092461741679:web:55c9300f8e5bbf3a6a2f9a',
};

// Initialize Firebase
// eslint-disable-next-line no-unused-vars
const firebase = initializeApp(firebaseConfig);
