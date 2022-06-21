import { useContext } from "react";
import CoreContext from "../contextApi/CoreContext";
import { useNavigate } from "react-router-dom";
import vipImage from '../static/resources/cartao-vip.png'
import "../static/styles/homePage.css"

function HomePageComponent() {
  const { spots, getHistories, checkPaymentValue } = useContext(CoreContext);
  const navigate = useNavigate();

  function goToHistory() {
    getHistories();
    navigate("/history")
  }

  function goToPaymentPage() {
    checkPaymentValue();
    navigate("/payment");
  }

  return (
    <div id="homePageContainer">
      <div id="menuBar">
        <button onClick={ goToHistory } >my History</button>
        <button onClick={ goToPaymentPage }>Pendent Payments</button>
      </div>
      <div id="spotsContainer">
        { spots && spots.sort((a, b) => a.id > b.id ? 1 : -1).map((spot, index) => (
          <div className="spotContainer" key={ index } style={{ background: spot.available ? "#FFFAFF" : "#D8315B", color: spot.available ? "#D8315B" : "#FFFAFF" }}>
            <h1 className="spotId">{spot.id}º spot</h1>
            <h3>Value: { spot.spotType.value } USD/h</h3>
            { spot.available && <button onClick={ () => navigate(`/parking/${spot.id}`) }>Rent this slot</button> }
            { spot.spotType.type === "vip" && <img src={ vipImage } id="vipImage" alt="vip-spot"/> }
            { !spot.available && <span id="occupiedText">Occupied</span>}
          </div>
        ))
        }
    </div>
    </div>
  )
}

export default HomePageComponent;
