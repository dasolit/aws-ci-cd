let main = {
  init: function() {
    $("#btn-save").on("click", ()=>{
      this.save();
    });
    $("#btn-update").on("click", () =>{
      this.update();
    });
    $("#btn-delete").on("click", () =>{
      this.delete();
    });
  },
  save: function() {
    let data = {
      title : $("#title").val(),
      author : $("#author").val(),
      content : $("#content").val()
    };

    $.ajax({
      type:"POST",
      url: "/api/v1/posts",
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify(data)
    }).done(function(response) {
      if(response.status === 500) {
        alert("글 등록에 실패하였습니다.");
      }else {
        alert("글이 등록되었습니다.");
        location.href="/";
      }
    }).fail(function (error) {
      alert("오류가 발생하였습니다.");
    })
  },
  update: function() {
    let data = {
      title: $("#title").val(),
      content: $("#content").val()
    };

    let id = $("#id").val();

    $.ajax({
      type: "PUT",
      url: "/api/v1/posts/" + id,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify(data)
    }).done(function(response) {
      if (response.status===500){
        alert("글 수정에 실패하였습니다.");
      }else {
        alert("글이 수정되었습니다.");
        location.href="/";
      }
    }).fail(function (error){
      alert("오류가 발생하였습니다.");
    });
  },
  delete: function() {
    let id = $("#id").val();

    $.ajax({
      type: "DELETE",
      url: "/api/v1/posts/" + id,
      dataType: "json",
      contentType: "application/json; charset=uft-8"
    }).done(function(response){
      if(response.status===500){
        alert("글 삭제에 실패하였습니다.");
      }else{
        alert("글이 삭제되었습니다.")
        location.href="/";
      }
    }).fail(function(error){
      alert("오류가 발생하였습니다.");
    })
  }
}

main.init();