$(function(){
//当键盘键被松开时发送Ajax获取数据
	$('#text').keyup(function(){
		var keywords = $(this).val();
		if (keywords=='') { $('#word').hide(); return };
		$.ajax({
			url: '/chat/searchFriend?keyword=' + keywords,
			dataType: 'json',
			success:function(res){
				$('#word').empty().show();
				if (res['code'] === 400)
				{
					$('#word').append('<div class="error">Not find  "' + keywords + '"</div>');
					return ;
				}
				var p  = res['data'];
				for(var i = 0 ;i < p.length;i++){
					var id = p[i]['id'];
					var username = p[i]['username'];
					var userImg = p[i]['userImg'];
					var tr = '<div class="chat-list-people" id='+id+'>' +
						'<div><img src='+userImg+' alt="头像"></div>' +
						' <div class="chat-name">' +
						'<p style="color: #1E90FF">'+username+'</p>' +
						'</div>' +
						'</div>';
					$('#word').append(tr);
				}
			},
		})
	});
//点击搜索数据复制给搜索框
	$(document).on('click','.click_work',function(){
		var word = $(this).text();
		$('#text').val(word);
		$('#word').hide();
		// $('#texe').trigger('click');触发搜索事件
	})

})