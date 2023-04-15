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
        <div className="row mt-5" style={{ display: 'flex', justifyContent: 'center' }}>
            <div className="col-md-5" style={{ backgroundColor: '#F5A9B8', padding: '30px', borderRadius: '10px' }}>
                <form onSubmit={onFormSubmit}>
                    <div className="form-group">
                        <label htmlFor="name" style={{ fontSize: '24px', color: 'gray' }}>Book name</label>
                        <h3 style={{ fontSize: '18px', color: 'gray' }}>Enter author name to see more data from dbpedia about him</h3>
                        <input type="text"
                               className="form-control"
                               id="name"
                               name="name"
                               required
                               placeholder="Enter book name"
                               style={{ fontSize: '18px', marginTop: '10px' }}
                        />
                    </div>
                    <br/>
                    <button id="submit" type="submit" className="btn btn-primary" style={{ backgroundColor: '#E6E6E6', color: 'gray', border: 'none', borderRadius: '5px', fontSize: '20px' }}>Submit</button>
                </form>
            </div>
        </div>

    )
}
export default SearchBook;