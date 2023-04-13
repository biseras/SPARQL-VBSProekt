import React, { useState } from 'react';
import Service from "../../repository/Repository";
import { useNavigate } from 'react-router-dom';

const SearchBook = (props) => {
    const [responestate, updatedata] = React.useState()
    const history = useNavigate();
    const onFormSubmit =async (e) => {
        e.preventDefault();
        const name = e.target.name.value;
        let response=await Service.searchBook(name)
        console.log(response)
        updatedata(response.data)
        history('/resultbook', {state:response});
    }
    return (
        <div className="row mt-5">
            <div className="col-md-5">
                <form onSubmit={onFormSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Book name</label>
                        <h3>Enter author name to see more data from dbpedia about him</h3>
                        <input type="text"
                               className="form-control"
                               id="name"
                               name="name"
                               required
                               placeholder="Enter book name"
                               // onChange={handleChange}
                        />
                    </div>
                    <br/>
                    <button id="submit" type="submit" className="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>

    )
}
export default SearchBook;