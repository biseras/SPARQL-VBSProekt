import React from 'react';
import Service from "../../repository/Repository";
import {useLocation} from "react-router-dom";

const SearchResultBook = ({ data }) => {
    const location=useLocation()
    const mydata=location.state
    const onFormSubmit =async (e) => {
        e.preventDefault();
        console.log(mydata)
    }
    return (
        <div>
            <p>{mydata.abstract}</p>
        </div>
    );
};

export default SearchResultBook;