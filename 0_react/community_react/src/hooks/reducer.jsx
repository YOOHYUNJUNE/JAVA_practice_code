export const postReducer = (posts, action) => {
    switch(action.type) {
        case 'SET_POSTS':
            return action.payload;
        case 'ADD_POST':
            return [...posts, action.payload];
    }
}