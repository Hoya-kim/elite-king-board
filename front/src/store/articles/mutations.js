import * as Article from './types';

export default {
  [Article.FETCH_ARTICLES]: (state, payload) => {
    state.articles = payload;
  },
  [Article.ADD_ARTICLE]: (state, payload) => {
    state.articles = [...state.articles, payload.data];
  },
};
