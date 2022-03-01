import * as User from './types';

export default {
  [User.FETCH_USER]: (state) => {
    return state.user;
  },
};
