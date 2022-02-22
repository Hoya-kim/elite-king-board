import { createStore } from 'vuex';
import articleModule from './articles';

export default createStore({
  modules: {
    article: articleModule,
  },
});
