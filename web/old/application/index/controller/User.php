<?php


namespace app\index\controller;

use app\index\model\user as usermodel;
use think\Controller;

class User extends Controller
{
    public function login(){
        $data['name'] = input('username');
        $data['password'] = md5(input('password'));
        $res = db('user')->where($data)->find();
        if($res){
            $map = db('map')->where('key', "login_count")->find();
            $num = $map['value'];
            cache(md5($num), $res);
            $map['value']++;
            $res = db('map')->where('key', 'login_count')->update(['value'=>$map['value']]);
            return md5($num);
        }
        return 'false';
    }

    public function create_user(){
        $data['name'] = input('username');
        $data['password'] = md5(input('password'));
        $data['favor_1'] = $data['favor_2'] = $data['favor_3'] = $data['favor_4'] = $data['favor_5']  = 2;
        $res = usermodel::create($data);
        if($res){
            return 'true';
        }else{
            return 'false';
        }
    }

    public function get($session_key){
        $user = cache($session_key);
        unset($user['id']);
        return json($user);
    }

    public function edit_favor(){
        $session_key = input('session_key');
        $user = cache($session_key);

        $data['favor_1'] = input('favor_1');
        $data['favor_2'] = input('favor_2');
        $data['favor_3'] = input('favor_3');
        $data['favor_4'] = input('favor_4');
        $data['favor_5'] = input('favor_5');
        $res = db('user')->where('id', $user['id'])->data($data)->update();
        if($res){
            return 'true';
        }
        return 'false';
    }
}