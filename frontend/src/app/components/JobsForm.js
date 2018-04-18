import React from 'react';
import TextField from 'material-ui/TextField';
import DatePicker from 'material-ui/DatePicker';
import TimePicker from 'material-ui/TimePicker';
import RaisedButton from 'material-ui/RaisedButton';

const style = {
  margin: 12,
};

class JobsForm extends React.Component {

  render() {
    return (
      <div>
        <TextField
          defaultValue="Default Value"
          floatingLabelText="Floating Label Text"
        />
        <DatePicker hintText="Portrait Dialog" />
        <TimePicker
          hintText="12hr Format with auto ok"
          autoOk={true}
        />
        <RaisedButton label="Submit" secondary={true} style={style}/>
      </div>
    )
  }
}

export default JobsForm;