import * as Article from './types';

export default {
  [Article.FETCH_ARTICLES]: (state) => {
    return state.articles;
  },
};
