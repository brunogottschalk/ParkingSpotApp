import { createContext, useState } from 'react';
import parkingSpotApi from '../services/ParkingSpotApi';

const LoginContext = createContext("")

function LoginContextProvider({ children }) {

  const [authorizationToken, setAuthorizationToken] = useState(JSON.parse(localStorage.getItem("authorizationToken")) || undefined);

  async function getAuthorizationToken(username, password) {
    const token = await parkingSpotApi.loginRequest(username, password);
    setAuthorizationToken(token);
    if (token.status === 200) {
      localStorage.setItem("authorizationToken", JSON.stringify(token));
    }
  }

  const contextValues = {
    authorizationToken,
    getAuthorizationToken,
  };

  return (
    <LoginContext.Provider value={ contextValues }>{ children }</LoginContext.Provider>
  )
}

export { LoginContextProvider };
export default LoginContext;
