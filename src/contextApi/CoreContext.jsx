import { createContext, useContext } from "react";
import LoginContext from "./LoginContext";
import { useState, useEffect } from "react";
import parkingSpotApi from "../services/ParkingSpotApi";


const CoreContext = createContext("");

function CoreContextProvider({ children }) {
  const [spots, setSpots] = useState(undefined);
  const [parkingResponseMessage, setParkingResponseMessage] = useState(undefined);
  const [histories, setHistories] = useState(undefined);
  const [paymentValue, setPaymentValue] = useState(undefined);
  const [completePaymentResponse, setCompletePaymentResponse] = useState(undefined);
  const { authorizationToken } = useContext(LoginContext);

  async function getSpots() {
    const response = await parkingSpotApi.parkingSpots(authorizationToken.content.token)
    setSpots(response)
  }

  useEffect(() => {
    if (authorizationToken) {
      getSpots();
    }
  }, [authorizationToken])

  async function requestNewParkingSpot(spotId, departureDate) {
    const response = await parkingSpotApi.newParkingRequest(spotId, departureDate, authorizationToken.content.token);
    setParkingResponseMessage(response);
  }

  async function getHistories() {
    const result = await parkingSpotApi.getHistories(authorizationToken.content.token);

    setHistories(result);
  }

  async function checkPaymentValue() {
    const paymentResponse = await parkingSpotApi.checkPayments(authorizationToken.content.token);
    setPaymentValue(paymentResponse);
  }

  async function completePayment(value) {
    const response = await parkingSpotApi.completePayment(value, authorizationToken.content.token);
    await getSpots();
    setCompletePaymentResponse(response);
  }

  const contextValues = {
    getSpots,
    spots,
    requestNewParkingSpot,
    parkingResponseMessage,
    getHistories,
    histories,
    checkPaymentValue,
    paymentValue,
    completePayment,
    completePaymentResponse,
  };

  return (
    <CoreContext.Provider value={ contextValues }>{ children }</CoreContext.Provider>
  )

}

export { CoreContextProvider };
export default CoreContext;
