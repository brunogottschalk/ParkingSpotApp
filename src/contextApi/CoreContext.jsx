import { createContext, useContext } from "react";
import LoginContext from "./LoginContext";
import { useState, useEffect } from "react";
import parkingSpotApi from "../services/ParkingSpotApi";


const CoreContext = createContext("");

function CoreContextProvider({ children }) {
  const [spots, setSpots] = useState(undefined);
  const { authorizationToken } = useContext(LoginContext);

  useEffect(() => {
    if (authorizationToken) {
      parkingSpotApi.parkingSpots(authorizationToken.content.token).then((response) => setSpots(response));
    }
  }, [authorizationToken])

  const contextValues = {
    spots
  };

  return (
    <CoreContext.Provider value={ contextValues }>{ children }</CoreContext.Provider>
  )

}

export { CoreContextProvider };
export default CoreContext;
