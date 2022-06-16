import { useContext, useEffect } from "react";
import { useInput } from "../hooks/useInputs";
import { useNavigate } from "react-router-dom";
import CoreContext from "../contextApi/CoreContext";
import loadingLogo from '../static/resources/1476.gif';
import "../static/styles/paymentPageStyle.css";

function PaymentPageComponent() {
  const { paymentValue, completePayment, completePaymentResponse, getSpots } = useContext(CoreContext);
  const { value: money, bind: bindMoney, reset: resetMoney } = useInput('');
  const navigate = useNavigate();

  function confirmPayment() {
    resetMoney();
    completePayment(money);
  }

  useEffect(() => {
    if (completePaymentResponse && completePaymentResponse.status === 200) {
      getSpots();
    }
  }, [])

  return (
    <div id="paymentContainer">
      { !paymentValue && <img id="loadingImage" src={ loadingLogo } alt="loading"></img>}
      { paymentValue && <div id="menuBar">
        <button onClick={ () => navigate("/home") }>Back to Home Page</button>
      </div> }
      { paymentValue && paymentValue.status === 400 && <h4 style={{ color: "#fffaff", textAlign: "center"}}>{ paymentValue.content.message }</h4> }
      { 
        paymentValue  && paymentValue.status === 200 && 
        <div id="paymentForm">
          <label htmlFor="moneyInput">
            <span>Money received: </span>
            <input type="text" id="moneyInpuy" { ...bindMoney } />
            <span>USD</span>
          </label>
          <button id="paymentButton" onClick={ confirmPayment } >confirm payment</button>
          { completePaymentResponse && completePaymentResponse.status !== 200 && <h4>{ completePaymentResponse.content.message }</h4>}
          { completePaymentResponse && completePaymentResponse.status === 200 && <h4>{ completePaymentResponse.content.message}</h4>}
          <div id="valueToPay">
            <h4>value to pay: { paymentValue.content.valueToPay } USD</h4>
            { completePaymentResponse && completePaymentResponse.status === 200 && <h4>Change: { completePaymentResponse.content.change }</h4>}
          </div>
        </div>
      }
    </div>
  )
}

export default PaymentPageComponent;
