import * as User from './types';

export default {
  [User.FETCH_USER]: async (store, payload) => {
    try {
      const { data } = payload;
      if(!data) return;
      await store.commit(User.FETCH_USER, data);
      return data;
    } catch (error) {
      console.error(error);
    }
  },
};
