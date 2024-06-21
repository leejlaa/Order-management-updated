import Cookies from 'js-cookie';

// Function to save customer ID in a cookie
export const saveCustomerEmailToCookie = (email) => {
    Cookies.set('email', email); // expires in 7 days
};

// Function to get customer ID from cookie
export const getCustomerEmailFromCookie = () => {
    return Cookies.get('email');
};

