import { useContext } from "react";
import CoreContext from "../contextApi/CoreContext";

function HomePageComponent() {
  const { spots } = useContext(CoreContext); 
  return (
    <div>
        { spots && spots.sort((a, b) => a.id > b.id ? 1 : -1).map((spot, index) => (
          <div className="spot_container" key={ index } style={{ background: spot.available ? "#40F99B" : "#E85D75"}}>
            <h1>{spot.id}º spot</h1>
            <h3>Value: { spot.spotType.value } USD</h3>
            <h3 style={
              { color: spot.spotType.type === "vip" ? "#E600FF" : "#F5FBEF"}
              }>
                { spot.spotType.type }
              </h3>
            <button onClick={(event) => console.log(event.target)}>Rent this slot</button>
          </div>
        ))
        }
    </div>
  )
}

export default HomePageComponent;
