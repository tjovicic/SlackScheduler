import React from 'react';
import {TableHeader, Table, TableBody, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';
import axios from "axios";
import {FloatingActionButton} from "material-ui";
import {ContentRemove} from "material-ui/svg-icons/index";
import uuid from 'uuid/v1';

class JobsTable extends React.Component {

  constructor(props) {
    super(props);
    this.state = {jobs: []};

    this.handleJobDeletion = this.handleJobDeletion.bind(this);
  }

  componentDidMount() {
    axios.get('/api/jobs')
      .then(res => {
        const jobs = res.data;
        this.setState({jobs});
      })
  }

  handleJobDeletion(id) {
    axios.delete('/api/jobs/'+id)
      .then(res => {
        debugger;
        this.setState(this.state.jobs.filter(job => job.id !== id));
      })
  }

  generateRows() {
    return this.state.jobs.map(job => {
      return (
        <TableRow key={uuid()}>
          <TableRowColumn>{job.id}</TableRowColumn>
          <TableRowColumn>{job.text}</TableRowColumn>
          <TableRowColumn>{job.time}</TableRowColumn>
          <TableRowColumn>{job.channel}</TableRowColumn>
          <TableRowColumn>{job.status}</TableRowColumn>
          <TableRowColumn>
            <FloatingActionButton mini={true} secondary={true} onClick={() => this.handleJobDeletion(job.id)}>
              <ContentRemove/>
            </FloatingActionButton>
          </TableRowColumn>
        </TableRow>
      );
    })
  }

  render() {
    return (
      <Table>
        <TableHeader adjustForCheckbox={false} displaySelectAll={false}>
          <TableRow>
            <TableHeaderColumn>ID</TableHeaderColumn>
            <TableHeaderColumn>Text</TableHeaderColumn>
            <TableHeaderColumn>Time</TableHeaderColumn>
            <TableHeaderColumn>Channel</TableHeaderColumn>
            <TableHeaderColumn>Status</TableHeaderColumn>
            <TableHeaderColumn/>
          </TableRow>
        </TableHeader>
        <TableBody displayRowCheckbox={false}>
          {this.generateRows()}
        </TableBody>
      </Table>
    )
  }
}

export default JobsTable;