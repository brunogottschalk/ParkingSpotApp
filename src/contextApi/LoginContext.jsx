import { createContext, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import parkingSpotApi from '../services/ParkingSpotApi';

const LoginContext = createContext("")

function LoginContextProvider({ children }) {
  const navigate = useNavigate();
  const location = useLocation();
  const [authorizationToken, setAuthorizationToken] = useState(undefined);

  async function getAuthorizationToken(username, password) {
    const token = await parkingSpotApi.loginRequest(username, password);
    setAuthorizationToken(token);
  }

  useEffect(() => {
    if (!authorizationToken && location.pathname !== "/login") {
      navigate("/login");
    }
  });

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
