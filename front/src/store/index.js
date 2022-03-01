import { createStore } from 'vuex';
import userModule from './user';
import articleModule from './articles';

export default createStore({
  modules: {
    user: userModule,
    article: articleModule,
  },
});
