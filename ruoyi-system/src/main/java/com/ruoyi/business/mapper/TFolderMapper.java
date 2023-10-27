package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.TFolder;

import java.util.List;

/**
 * 文件夹Mapper接口
 *
 * @author ruoyi
 * @date 2023-10-11
 */
public interface TFolderMapper {
    /**
     * 查询文件夹列表
     *
     * @param userId 用户id
     * @return 列表
     */
    public List<TFolder> selectTFolderList(Long userId);

    /**
     * 查询文件夹详情
     * @param id 文件夹id
     * @return 实体
     */
    public TFolder selectTFolderById(Long id);
    /**
     * 新增文件夹
     *
     * @param tFolder 文件夹
     * @return 行数
     */
    public int insertTFolder(TFolder tFolder);

    /**
     * 修改文件夹
     * @param tFolder 文件夹
     * @return 行数
     */
    public int updateTFolder(TFolder tFolder);

    /**
     * 删除文件夹
     *
     * @param id 文件夹id
     * @return 行数
     */
    public int deleteTFolderById(Long id);
}
