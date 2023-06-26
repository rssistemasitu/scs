import { CSSProperties } from 'react'
import { BarLoader, PropagateLoader } from 'react-spinners';

const override: CSSProperties = {
    display: "block",
    margin: "0 auto",
};

function Spinner() {
  return (
    <div>
        <BarLoader
        color={"#ff1a1a"}
        loading={true}
        cssOverride={override}
        height={2.5}
        width={300}
        speedMultiplier={0.5}
      />
    </div>
  )
}

export default Spinner