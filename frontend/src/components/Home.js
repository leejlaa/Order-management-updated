// import React, { useState } from 'react';

// const Home = () => {
//     const [email, setEmail] = useState('');
//     const [password, setPassword] = useState('');

//     const handleLogin = () => {
//         // Add your login logic here
//     };

//     const handleSignUp = () => {
//         // Add your sign up logic here
//     };

//     return (
//         <div>
//             <h1>Login</h1>
//             <input
//                 type="email"
//                 placeholder="Email"
//                 value={email}
//                 onChange={(e) => setEmail(e.target.value)}
//             />
//             <input
//                 type="password"
//                 placeholder="Password"
//                 value={password}
//                 onChange={(e) => setPassword(e.target.value)}
//             />
//             <button onClick={handleLogin}>Login</button>
//             <button onClick={handleSignUp}>Sign Up</button>
//         </div>
//     );
// };

// export default Home;
import React, { useState } from 'react';
import { getUsers } from '../services/api';

const Home = () => {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState(null);

    const handleFetchUsers = async () => {
        try {
            const usersData = await getUsers();
            setUsers(usersData);
            setError(null);
        } catch (error) {
            setError('Error fetching users');
        }
    };

    return (
        <div>
            <button onClick={handleFetchUsers}>Fetch Users</button>
            {error && <p>{error}</p>}
            <ul>
                {users}
            </ul>
        </div>
    );
};

export default Home;
