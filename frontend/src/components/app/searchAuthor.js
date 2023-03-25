import React from 'react';

const searchAuthor = (props) => {
    const onFormSubmit = (e) => {
        e.preventDefault();
        const name = e.target.name.value;

        props.onsearch(name);
    }
    return (
        <div className="row mt-5">
            <div className="col-md-5">
                <form onSubmit={onFormSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Author name</label>
                        <h3>Enter author name to see more data from dbpedia about him</h3>
                        <input type="text"
                               className="form-control"
                               id="name"
                               name="name"
                               required
                               placeholder="Enter author name"
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
export default searchAuthor;