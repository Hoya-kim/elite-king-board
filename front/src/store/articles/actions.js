import * as Article from './types';
import axios from 'axios';

export default {
  // eslint-disable-next-line no-unused-vars
  [Article.FETCH_ARTICLES]: async (store, payload) => {
    try {
      // const { data } = await axios.get(`/articles/${ payload.pageNum }`);
      const { result } = await axios.get('http://localhost:8080/articles');
      console.log({result});
      const { data } = result;
      console.log({data});
      await store.commit(Article.FETCH_ARTICLES, data);
      return data;
    } catch (error) {
      console.error(error);
    }
  },
  [Article.ADD_ARTICLE]: async (store, payload) => {
    try {
      const { data } = await axios.get('/articles');
      if (data) {
        await store.commit(Article.ADD_ARTICLE, payload);
      }
    } catch (error) {
      console.error(error);
    }
  },
};
