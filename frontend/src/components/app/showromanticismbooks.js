import React from "react";

const Showrombooks = (props) => {
    console.log("tuj", props)
    return (
        <h1>{props.show.abstract}</h1>

    );
}

export default Showrombooks;