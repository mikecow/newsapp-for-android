<?php


namespace app\index\controller;


class news
{
    private $appkey = 'b6e132beeba19ecf';//你的appkey
    private $get_news_url = 'https://api.jisuapi.com/news/get';
    private $get_channel_url = 'https://api.jisuapi.com/news/channel';

    /**
     * @param $channel  string 栏目
     * @param $start    int    开始
     * @param $num
     * @return bool|\think\response\Json
     */
    public function get_news_list($channel, $start, $num){
        $tokenUrl = $this->get_news_url."?channel={$channel}&start={$start}&num={$num}&appkey={$this->appkey}";
        $http = new Http();
        $token_data = $http->url($tokenUrl);
        
        $arr = json_decode($token_data, TRUE);

        if($arr['msg'] == "ok"){
            $arr['endNum'] = $start + $num;
            return json($arr['result']['list']);
        }else{
            return 'false';
        }
    }

    /**
     * @return bool|\think\response\Json 返回所有栏目
     */
    public function get_chanel(){
        $tokenUrl = $this->get_channel_url."?appkey={$this->appkey}";
        $http = new Http();
        $token_data = $http->url($tokenUrl);
        $arr = json_decode($token_data, TRUE);


        if($arr['msg'] == "ok"){
            return json($arr['result']);
        }else{
            return false;
        }
    }

    /**
     * @param $session_key string session_key
     * @param $start int 起始新闻地址
     * @return \think\response\Json 推荐新闻序列
     */
    public function get_personal_news($session_key, $start){
        $user = cache($session_key);
        $list = array();
        $favor = $user['favor_1'];
        
        $tokenUrl = $this->get_news_url."?channel=新闻&start={$start}&num={$favor}&appkey={$this->appkey}";
        $http = new Http();
        $token_data = $http->url($tokenUrl);
        $arr = json_decode($token_data, TRUE);
        foreach ($arr['result']['list'] as $new){
            array_push($list, $new);
        }
        $start+= $favor;


        $favor = $user['favor_2'];
        $tokenUrl = $this->get_news_url."?channel=财经&start={$start}&num={$favor}&appkey={$this->appkey}";
        $token_data = $http->url($tokenUrl);
        $arr = json_decode($token_data, TRUE);
        foreach ($arr['result']['list'] as $new){
            array_push($list, $new);
        }
        $start+= $favor;


        $favor = $user['favor_3'];
        $tokenUrl = $this->get_news_url."?channel=体育&start={$start}&num={$favor}&appkey={$this->appkey}";
        $token_data = $http->url($tokenUrl);
        $arr = json_decode($token_data, TRUE);
        foreach ($arr['result']['list'] as $new){
            array_push($list, $new);
        }
        $start+= $favor;

        $favor = $user['favor_4'];
        $tokenUrl = $this->get_news_url."?channel=娱乐&start={$start}&num={$favor}&appkey={$this->appkey}";
        $token_data = $http->url($tokenUrl);
        $arr = json_decode($token_data, TRUE);
        foreach ($arr['result']['list'] as $new){
            array_push($list, $new);
        }
        $start+= $favor;

        $favor = $user['favor_5'];
        $tokenUrl = $this->get_news_url."?channel=军事&start={$start}&num={$favor}&appkey={$this->appkey}";
        $token_data = $http->url($tokenUrl);
        $arr = json_decode($token_data, TRUE);
        foreach ($arr['result']['list'] as $new){
            array_push($list, $new);
        }
        
        return json($list);
    }

    /**
     * 保存浏览记录
     * @param $news \app\index\model\news
     * @return string
     */
    public function saveNews(){
        $session_key = input('session_key');
        $user = cache($session_key);

        $data['user_id'] = $user['id'];
        $data['title'] = input('title');

        $res = db('news')->where('user_id', $data['user_id'])->where('title', $data['title'])->find();
        if($res){
            return $res['id'];
        }

        $data['time'] = input('time');
        $data['src'] = input('src');
        $data['category'] = input('category');
        $data['pic'] = input('pic');
        $data['url'] = input('url');
		
		$index = 0;
		if($data['category'] == "news"){

			if($user['favor_2'] != 1){
				$user['favor_2']--;
				$index++;
			}
			
			if($user['favor_3'] != 1){
				$user['favor_3']--;
				$index++;
			}
			
			if($user['favor_4'] != 1){
				$user['favor_4']--;
				$index++;
			}
			
			if($user['favor_5'] != 1){
				$user['favor_5']--;
				$index++;
			}
			
			$user['favor_1'] += $index;
		}else if($data['category'] == "finance"){
			if($user['favor_1'] != 1){
				$user['favor_1']--;
				$index++;
			}
			
			if($user['favor_3'] != 1){
				$user['favor_3']--;
				$index++;
			}
			
			if($user['favor_4'] != 1){
				$user['favor_4']--;
				$index++;
			}
			
			if($user['favor_5'] != 1){
				$user['favor_5']--;
				$index++;
			}
			
			$user['favor_2'] += $index;
		}else if($data['category'] == "sports"){
			if($user['favor_1'] != 1){
				$user['favor_1']--;
				$index++;
			}
			
			if($user['favor_2'] != 1){
				$user['favor_2']--;
				$index++;
			}
			
			if($user['favor_4'] != 1){
				$user['favor_4']--;
				$index++;
			}
			
			if($user['favor_5'] != 1){
				$user['favor_5']--;
				$index++;
			}
			
			$user['favor_3'] += $index;
		}else if($data['category'] == "ent"){
			if($user['favor_1'] != 1){
				$user['favor_1']--;
				$index++;
			}
			
			if($user['favor_2'] != 1){
				$user['favor_2']--;
				$index++;
			}
			
			if($user['favor_3'] != 1){
				$user['favor_3']--;
				$index++;
			}
			
			if($user['favor_5'] != 1){
				$user['favor_5']--;
				$index++;
			}
			
			$user['favor_4'] += $index;
		}else if($data['category'] == "mil"){
			if($user['favor_1'] != 1){
				$user['favor_1']--;
				$index++;
			}
			
			if($user['favor_2'] != 1){
				$user['favor_2']--;
				$index++;
			}
			
			if($user['favor_3'] != 1){
				$user['favor_3']--;
				$index++;
			}
			
			if($user['favor_4'] != 1){
				$user['favor_4']--;
				$index++;
			}

			
			$user['favor_5'] += $index;
		}
		
		$res = db('user')->update($user);
		
        $res = db('news')->insert($data);
        if($res){
            $res = db('news')->where('user_id', $data['user_id'])->where('title', $data['title'])->find();
            if($res){
                return $res['id'];
            }
        }
        return 'false';
    }

    /**
     * 收藏新闻
     * @return string
     */
    public function save_con(){
        $session_key = input('session_key');
        $user = cache($session_key);

        $data['user_id'] = $user['id'];
        
        $data['news_id'] = input('news_id');
        $res = db('rec')->insert($data);
        if($res){
            return 'true';
        }else{
            return 'false';
        }
    }

    /**
     * 返回个人浏览记录
     * @return \think\response\Json
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\ModelNotFoundException
     * @throws \think\exception\DbException
     */
    public function select_person_news(){
        $session_key = input('session_key');
        $user = cache($session_key);

        $news_list = db('news')->where('user_id', $user['id'])->select();
        
        return json($news_list);
    }

    /**
     * 返回个人收藏记录
     * @return \think\response\Json
     * @throws \think\db\exception\DataNotFoundException
     * @throws \think\db\exception\ModelNotFoundException
     * @throws \think\exception\DbException
     */
    public function select_person_con(){
        $session_key = input('session_key');
        $user = cache($session_key);

        $news_list = db('news')->alias('n')->join('rec r', "n.id = r.news_id")->where('n.user_id', $user['id'])->select();
        return json($news_list);
    }
    

}