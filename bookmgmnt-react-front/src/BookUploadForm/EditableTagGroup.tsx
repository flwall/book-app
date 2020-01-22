import React from "react";
import "antd/dist/antd.css";
import { Tag, Input, Icon } from "antd";
import { TweenOneGroup } from "rc-tween-one";

interface TagState{
  
  inputVisible:boolean;
  inputValue:string;

}
interface TagProps{
  //onChange:(e:{value:string})=>void;
  tags:Array<string>;
  setTags:(tags:Array<string>)=>void;

}
class EditableTagGroup extends React.Component<TagProps,TagState> {
  constructor(prop:any) {
    super(prop);
    this.state = {
      inputVisible: false,
      inputValue: ""
    };

    
  }
  

  handleClose = (removedTag: string) => {
    const tags = this.props.tags.filter(tag => tag !== removedTag);
    
    this.props.setTags(tags);
  };

  private input:Input|null=null;
  showInput = () => {
    this.setState({ inputVisible: true }, () => this.input!.focus());
  };

  handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({ inputValue: e.target.value });
  };

  handleInputConfirm = () => {
    const { inputValue } = this.state;
    let { tags } = this.props;
    if (inputValue && tags.indexOf(inputValue) === -1) {
      tags = [...tags, inputValue];
    }
    this.props.setTags(tags);
    this.setState({
      inputVisible: false,
      inputValue: ""
    });
  };

  saveInputRef = (input: any) => (this.input = input);

  forMap = (tag: any) => {
    const tagElem = (
      <Tag
        closable
        onClose={(e: any) => {
          e.preventDefault();
          this.handleClose(tag);
        }}
      >
        {tag}
      </Tag>
    );
    return (
      <span key={tag} style={{ display: "inline-block" }}>
        {tagElem}
      </span>
    );
  };

  render() {
    const {  inputVisible, inputValue } = this.state;
    const {tags}=this.props;
    const tagChild = tags.map(this.forMap);
    return (
      <div>
        <div style={{ marginBottom: 16 }}>
          <TweenOneGroup
            enter={{
              scale: 0.8,
              opacity: 0,
              type: "from",
              duration: 100
            }}
            leave={{ opacity: 0, width: 0, scale: 0, duration: 200 }}
            appear={false}
          >
            {tagChild}
          </TweenOneGroup>
        </div>
        {inputVisible && (
          <Input
            ref={this.saveInputRef}
            type="text"
            size="small"
            style={{ width: 78 }}
            value={inputValue}
            onChange={this.handleInputChange}
            onBlur={this.handleInputConfirm}
            onPressEnter={this.handleInputConfirm}
          />
        )}
        {!inputVisible && (
          <Tag
            onClick={this.showInput}
            style={{ background: "#fff", borderStyle: "dashed" }}
          >
            <Icon type="plus" /> New Tag
          </Tag>
        )}
      </div>
    );
  }
}

export default EditableTagGroup;
