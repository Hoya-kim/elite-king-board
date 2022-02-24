import * as Article from './types';
import axios from 'axios';

export default {
  [Article.FETCH_ARTICLES]: async (store, payload) => {
    try {
      const { data } = await axios.get(`/articles/${ payload.pageNum }`);
      await store.dispatch(Article.FETCH_ARTICLES, payload);
      return data;
    } catch (error) {
      console.error(error);
    }
  },
  [Article.ADD_ARTICLE]: async (store, payload) => {
    try {
      const { data } = await axios.get('/articles');
      if (data) {
        await store.dispatch(Article.ADD_ARTICLE, payload);
      }
    } catch (error) {
      console.error(error);
    }
  },
};
