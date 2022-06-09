import { createContext, useState } from 'react';
import parkingSpotApi from '../services/ParkingSpotApi';

const LoginContext = createContext("")

function LoginContextProvider({ children }) {

  const [authorizationToken, setAuthorizationToken] = useState(undefined);

  async function getAuthorizationToken(username, password) {
    const token = await parkingSpotApi.loginRequest(username, password);
    setAuthorizationToken(token);
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
