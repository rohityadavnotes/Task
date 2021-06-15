<?php
class Food
{
    /*******************************************************************************************************************
	 *******************************************************************************************************************
	 **************************************** SET ITEM *************************************************************
	 *******************************************************************************************************************
	 ******************************************************************************************************************/
	public function setItem($itemName,$itemCategory,$itemRate,$itemImage)
	{
		$connection = DatabaseConnection::getConnection();

		$existedOrNotQuery = "SELECT * FROM category WHERE category_name='$itemCategory'";
		$existedOrNotCheck = mysqli_query($connection,$existedOrNotQuery);
		$response;

		if(mysqli_num_rows($existedOrNotCheck)>0)
		{
			$row = mysqli_fetch_array($existedOrNotCheck);
			$categoryId=$row[0];

			$newItemInsertQuery = "INSERT INTO item(category_id,name,rate,image) VALUES('$categoryId','$itemName','$itemRate','$itemImage')";
			$newItemInsertCheck = mysqli_query($connection,$newItemInsertQuery);
			
			if(!$newItemInsertCheck)
			{
				$response = array(
					"response_code"=>400,
					"response_message"=>'Insert Failed'
				);
			}
			else
			{
				$response = array(
				    "response_code"=>200,
					"response_message"=>'Insert Success'
				);
			}
		}
		else
		{
			$newCategoryInsertQuery = "INSERT INTO category(category_name,image) VALUES('$itemCategory','$itemImage')";
			$newCategoryCheck = mysqli_query($connection,$newCategoryInsertQuery);
			
			if(!$newCategoryCheck)
			{
				$response = array(
					"response_code"=>400,
					"response_message"=>'Insert Failed'
				);
			}
			else
			{
				$insertExistedOrNotQuery = "SELECT * FROM category WHERE category_name='$itemCategory'";
				$insertExistedOrNotCheck = mysqli_query($connection,$insertExistedOrNotQuery);
				
				if(mysqli_num_rows($insertExistedOrNotCheck)>0)
				{
					$row = mysqli_fetch_array($insertExistedOrNotCheck);
					$categoryId=$row[0];
					
					$newInsertQuery = "INSERT INTO item(category_id,name,rate,image) VALUES('$categoryId','$itemName','$itemRate','$itemImage')";
					$newInsertCheck = mysqli_query($connection,$newInsertQuery);
					
					if(!$newInsertCheck)
					{
						
						$response = array(
						    "response_code"=>400,
						    "response_message"=>'Insert Failed'
						    );
					}
					else
					{
						$response = array(
						    "response_code"=>200,
						    "response_message"=>'Insert Success'
						    );
					}
				}
			}
		}

        header('Content-type: application/json');
		echo json_encode($response);

		mysqli_close($connection);
	}
	/*******************************************************************************************************************
	 *******************************************************************************************************************
	 **************************************** GET CATEGORY *************************************************************
	 *******************************************************************************************************************
	 ******************************************************************************************************************/
	public function getCategories()
	{
        $connection = DatabaseConnection::getConnection();

		$query = "SELECT * FROM category";
		$result = mysqli_query($connection,$query);
		$response = array();

		while($row = mysqli_fetch_array($result))
		{
			array_push($response,array(
                "id"=>intval($row['id']),
                "category_name"=>$row['category_name'],
                "image"=>$row['image']
                )
            );
		}

        header('Content-type: application/json');
		echo json_encode(array("response_code"=>200,"response_message"=>'Fetch Category Success',"data"=>$response));

		mysqli_close($connection);
	}
	/*******************************************************************************************************************
     *******************************************************************************************************************
     **************************************** GET CATEGORY *************************************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/
	public function getItems($categoryId)
	{
        $connection = DatabaseConnection::getConnection();

		$query = "SELECT * FROM item WHERE category_id='$categoryId'";
		$result = mysqli_query($connection,$query);
		$response = array();
		
		while($row = mysqli_fetch_array($result))
		{
            array_push($response,array(
              "id"=>intval($row['id']),
              "category_id"=>intval($row['category_id']),
              "name"=>$row['name'],
              "rate"=>$row['rate'],
              "image"=>$row['image'])
            );
		}
			
        header('Content-type: application/json');
		echo json_encode(array("response_code"=>200,"response_message"=>'Fetch Item Success','data'=>$response));

		mysqli_close($connection);
	}
}
?>
