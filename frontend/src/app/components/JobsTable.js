import React from 'react';
import {
  Table,
  TableBody,
  TableHeader,
  TableHeaderColumn,
  TableRow,
  TableRowColumn,
} from 'material-ui/Table';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import ContentRemove from 'material-ui/svg-icons/content/remove';
import axios from "axios/index";


class JobsTable extends React.Component {

  componentDidMount() {
    axios.get('/api/jobs')
      .then(res => {
        debugger;
        const persons = res.data;
        this.setState({persons});
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
          <TableRow>
            <TableRowColumn>1</TableRowColumn>
            <TableRowColumn>Good Morning!</TableRowColumn>
            <TableRowColumn>2017-08-03</TableRowColumn>
            <TableRowColumn>My Channel</TableRowColumn>
            <TableRowColumn>Pending</TableRowColumn>
            <TableRowColumn>
              <FloatingActionButton mini={true} secondary={true}>
                <ContentRemove />
              </FloatingActionButton>
            </TableRowColumn>
          </TableRow>
          <TableRow>
            <TableRowColumn>1</TableRowColumn>
            <TableRowColumn>Good Morning!</TableRowColumn>
            <TableRowColumn>2017-08-03</TableRowColumn>
            <TableRowColumn>My Channel1</TableRowColumn>
            <TableRowColumn>Sent</TableRowColumn>
            <TableRowColumn>
              <FloatingActionButton mini={true} secondary={true}>
                <ContentRemove />
              </FloatingActionButton>
            </TableRowColumn>
          </TableRow>
        </TableBody>
      </Table>
    )
  }
}

export default JobsTable;