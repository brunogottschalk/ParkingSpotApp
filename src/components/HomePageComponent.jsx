import { useContext } from "react";
import CoreContext from "../contextApi/CoreContext";
import { useNavigate } from "react-router-dom"; 

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
    <div>
      <div id="menuBar">
        <button onClick={ goToHistory } >my History</button>
        <button onClick={ goToPaymentPage }>Pendent Payments</button>
      </div>
      <div id="spotsContainer">
        { spots && spots.sort((a, b) => a.id > b.id ? 1 : -1).map((spot, index) => (
          <div className="spot_container" key={ index } style={{ background: spot.available ? "#40F99B" : "#E85D75" }}>
            <h1>{spot.id}º spot</h1>
            <h3>Value: { spot.spotType.value } USD</h3>
            <h3 style={
              { color: spot.spotType.type === "vip" ? "#E600FF" : "#F5FBEF" }
              }>
                { spot.spotType.type }
              </h3>
            { spot.available && <button onClick={ () => navigate(`/parking/${spot.id}`) }>Rent this slot</button> }
          </div>
        ))
        }
    </div>
    </div>
  )
}

export default HomePageComponent;
